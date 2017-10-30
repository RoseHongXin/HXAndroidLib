# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/rose/Programs/AndroidSdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class label to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-ignorewarnings

#-dontwarn
#-dontoptimize
#-dontpreopt
#-dontshrink
#-dontskipnonpubliclibraryclasses
#-dontskipnonpubliclibraryclassmembers

# Jackson
-keepnames class * { *; }
-keepclassmembers class * {
     @com.fasterxml.jackson.annotation.JsonCreator *;
     @com.fasterxml.jackson.annotation.JsonProperty *;
     @com.fasterxml.jackson.databind.ext *;
     @com.fasterxml.jackson.databind.introspect *;
}
-dontwarn com.fasterxml.jackso‌​n.databind.**


# GalleryFinal
-keep class cn.finalteam.galleryfinal.widget.*{*;}
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}