# OTAFreezeTool / 冻结更新

> 借助 Device Owner 权限冻结 Android 系统 OTA 更新，让设备在你不想升级的时候保持当前版本。

OTAFreezeTool 是一个面向 Android 12+ 的系统工具应用。它通过 [Dhizuku](https://github.com/iamr0s/Dhizuku) 获取 Device Owner 权限，再调用 `DevicePolicyManager` 的 `SystemUpdatePolicy` 冻结期机制，阻止系统更新应用在指定时间段内安装 OTA。

应用本身不需要 root，界面使用 AOSP SettingsLib 的 Material 3 / Expressive 组件构建，外观与系统设置保持一致。

---

## 功能特性

- **一键冻结 / 解冻系统更新**
  - 开启后设置一个从当天开始、最长 90 天的系统更新冻结期。
  - 关闭后清除 `SystemUpdatePolicy` 和冻结期记录，恢复正常更新。
- **Device Owner 授权状态展示**
  - 自动检测 Dhizuku 是否初始化、是否已获得权限。
  - 未授权时点击开关会引导申请权限，授权成功后自动继续刚才的操作。
- **自动识别系统更新应用**
  - 扫描声明 `ACTION_MAIN` + `CATEGORY_DEFAULT` 的系统应用。
  - 识别包名以 `.updater` 结尾的应用，以及 `com.oplus.ota`、`com.hihonor.ouc` 等已知更新器。
- **兼容性检测**
  - 使用 [DexKit](https://github.com/LuckyPray/DexKit) 扫描目标更新应用的 dex，确认它会读取 `SystemUpdatePolicy.getFreezePeriods()` 或 `getPolicyType()`。
  - 目标应用不支持冻结机制时自动禁用开关，避免误操作。
- **状态实时刷新**
  - 监听 `DevicePolicyManager.ACTION_SYSTEM_UPDATE_POLICY_CHANGED` 广播，而不是轮询。
  - 显示当前冻结期的到期日期。
- **系统设置风格 UI**
  - 基于 SettingsLib 的 AppHeader、AppSwitch、Card、Footer、Slider 等组件。
  - 支持 Material 3 动态取色（跟随壁纸主题色）。
- **崩溃日志**
  - 集成 xCrash，捕获 Java / Native 崩溃并提供崩溃日志查看页。
- **关于与开源许可**
  - 内置开发者信息、依赖库和许可证列表。

---

## 工作原理

1. 应用通过 Dhizuku 获取 Device Owner 身份。
2. 从 Device Owner 的 Context 拿到 `DevicePolicyManager`，通过反射把它的 `mService` 替换为 `Dhizuku.binderWrapper()` 包装后的 `device_policy` binder。
3. 开启冻结时调用：

   ```java
   SystemUpdatePolicy policy = SystemUpdatePolicy.createPostponeInstallPolicy()
           .setFreezePeriods(freezePeriods);
   dpm.setSystemUpdatePolicy(admin, policy);
   ```

   `freezePeriods` 从当天开始，长度取 `SystemUpdatePolicy.FREEZE_PERIOD_MAX_LENGTH - 1`，跨年时截断到哨兵年的 12 月 31 日。

4. 关闭冻结时调用 `setSystemUpdatePolicy(admin, null)`，并清空冻结期记录。
5. 冻结期以 `MonthDay` 表示，按年循环；单次冻结期最长 90 天。

> 注意：这里的“冻结”是阻止 OTA 在冻结期内安装，并不是永久关闭系统更新。冻结期结束或手动解除后，系统仍可能恢复更新。

---

## 环境要求

| 项目 | 要求 |
| --- | --- |
| 系统版本 | Android 12+（`minSdk 31`） |
| 架构 | `arm64-v8a` |
| Device Owner | 需安装并激活 Dhizuku，且设备上没有其他 Device Owner |
| JDK | 17+ |
| Android SDK | Platform 37、Build Tools 37.0.0 |
| NDK | 29.0.14206865 |
| protoc | 构建 `GoogleSettingsLib` 的 Graph 模块时需要（或设置 `PROTOC` 环境变量） |

---

## 构建

```bash
git clone https://github.com/sbmatch/OTAFreezeTool.git
cd OTAFreezeTool

# Debug
./gradlew :app:assembleDebug

# Release
./gradlew :app:assembleRelease
```

Debug 产物：

```text
app/build/outputs/apk/debug/OTAFreeze_v1.1.apk
```

Release 签名通过环境变量配置：

```bash
export KEYSTORE_PATH=/path/to/keystore
export KEYSTORE_PASSWORD=your_password
export KEY_ALIAS=your_alias
export KEY_PASSWORD=your_key_password
```

未设置时回退到 `~/.android/debug.keystore`，适合本地构建，不适合正式发布。

### 本地依赖说明

`app/libs/` 下的以下本地依赖未纳入版本控制（根 `.gitignore` 忽略了 `libs/`）：

- `mmkv-SharedCpp-release.aar`
- `xcrash_lib-release.aar`
- `miui-utils.jar`
- `arm64-v8a/`、`x86_64/` 下的 native 库

克隆仓库后需要自行把这些文件放入 `app/libs/`，否则应用模块无法完成构建。

---

## 使用

1. 安装 [Dhizuku](https://github.com/iamr0s/Dhizuku)，按官方说明将其激活为 Device Owner。
2. 安装并打开 OTAFreezeTool。
3. 首次点击「冻结系统更新」时授予 Dhizuku 权限。
4. 主页会显示：
   - Device Owner 授权状态；
   - 检测到的系统更新应用、包名和版本；
   - 冻结开关及当前冻结期的到期时间。
5. 关闭开关即可解除冻结、恢复正常系统更新。

---

## 项目结构

```text
OTAFreezeTool/
├── app/                         主应用
│   ├── src/main/java/...        入口、设置页、Dhizuku / DevicePolicy 逻辑
│   ├── src/main/res/            界面资源与主题
│   ├── jni/                     native 代码（hidden_api_bypass、turbojpeg 等）
│   └── libs/                    本地 AAR / JAR（未纳入 git）
├── GoogleSettingsLib/           从 AOSP SettingsLib 裁剪并合并的 UI 组件库
├── hidden-api/                  隐藏 API 编译桩（compileOnly）
├── gradle/                      Gradle Wrapper 与版本配置
├── build.gradle                 根构建脚本
├── settings.gradle              模块声明
└── README.md
```

### GoogleSettingsLib

`GoogleSettingsLib` 把 AOSP SettingsLib 中多个独立的 Soong 模块合并进一个 Gradle library，目前包含：

- 主题与颜色：`SettingsTheme`、`Color`
- Preference 组件：`AppPreference`、`CardPreference`、`AppSwitchPreference`、`ButtonPreference`、`SliderPreference`、`FooterPreference`、`ExpandablePreference` 等
- 页面骨架：`CollapsingToolbarBaseActivity`
- 其他组件：`EntityHeaderWidgets`、`SettingsSpinner`、`HelpUtils`
- 跨进程与设置项图：`Ipc`、`Graph`
- 元数据驱动的 Preference 框架：`Preference`、`Metadata`、`DataStore`

构建脚本会：

- 合并各子模块的 Java / Kotlin / 资源；
- 把子模块各自的 `R` 引用重写为统一的 `settingslib.R`；
- 修正 `androidprv` 等私有主题属性；
- 为 `Graph` 用 `protoc` 生成 protobuf lite 类；
- 生成少量 aconfig flag 桩。

---

## 技术说明

- **隐藏 API 访问**：`hidden-api` 提供编译期桩，`app/jni` 中的 `hidden_api_bypass` 在运行时绕过部分隐藏 API 限制。
- **Dhizuku Binder 包装**：`PreferenceMainFragment#getWrappedDevicePolicyManager()` 将 `DevicePolicyManager` 内部的 binder 替换为 Dhizuku 包装后的 binder，从而以 Device Owner 身份调用系统服务。
- **DexKit 检测**：在后台线程扫描目标 APK，仅当更新器确实读取 `SystemUpdatePolicy` 时才允许开启冻结。
- **动态取色**：应用启动时从壁纸主题色生成 Material 3 配色。
- **崩溃处理**：xCrash 记录 Java/Native 崩溃，状态通过 MMKV 持久化，下次启动时进入崩溃日志页。
- **Xposed 模块入口（实验性）**：`hook/ModuleEntry` 基于 libxposed API，包含系统签名注入相关代码，目前仅作为实验性代码保留，未默认启用。

---

## 已知限制

- 需要 Device Owner 权限；普通应用无法直接使用。
- 不同厂商的系统更新应用行为不同，部分更新器可能不读取 `SystemUpdatePolicy`，此时冻结不会生效（应用会通过 DexKit 检测并禁用开关）。
- 冻结期最长 90 天且按年循环，不是永久禁用更新。
- 仅支持 `arm64-v8a`。
- 设备上已有其他 Device Owner 时，Dhizuku 无法再被设置为 Device Owner。

---

## 免责声明

本项目通过 Device Owner 权限修改系统更新策略，仅用于个人设备的系统维护。请确保你了解相关风险，并在操作前做好数据备份。因使用本工具造成的设备异常、数据丢失或更新问题，由使用者自行承担。

---

## 许可证

仓库中的 `LICENSE.txt` 沿用自上游 AndroLua（MIT）。应用内「关于 → 开源许可」页面列出了构建时使用的第三方依赖及其许可证。
