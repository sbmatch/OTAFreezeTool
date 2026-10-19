-dontpreverify

-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn edu.umd.cs.findbugs.annotations.SuppressFBWarnings

-dontwarn java.awt.**
-dontwarn javax.swing.**

-keepattributes Signature
-keepattributes MethodParameters
-dontwarn javax.lang.model.element.Modifier

-keep class ai.onnxruntime.** {
    public <init>(...);
    public <methods>;
}

-keep class com.sbmatch.androtool.hook.** {
    public <init>(...);
    public <methods>;
}

-keep class com.k2fsa.sherpa.onnx.** {
    public <init>(...);
}

-keep class miui.util.** {
    public <init>(...);
    public <methods>;
}

-keep class rikka.shizuku.** {
    public <init>(...);
}

-keep class com.github.sisong.** { 
    public <init>(...);
    public <methods>;
}

-keep class xcrash.TombstoneManager {
    public <init>(...);
    public <methods>;
}
-keep class xcrash.TombstoneParser { 
    public <init>(...);
    public <methods>;
}

-keep class android.support.annotation.Keep
-keep public class com.google.android.vending.licensing.ILicensingService
-keep public class com.google.vending.licensing.ILicensingService
-keep,allowshrinking class * extends androidx.startup.Initializer
-keep class * implements androidx.versionedparcelable.VersionedParcelable
-keep public class androidx.versionedparcelable.ParcelImpl

-printmapping mapping.txt

-optimizationpasses 5

# 使用自定义字典混淆
-classobfuscationdictionary chinese_dictionary.txt
-obfuscationdictionary chinese_dictionary.txt
-packageobfuscationdictionary chinese_dictionary.txt