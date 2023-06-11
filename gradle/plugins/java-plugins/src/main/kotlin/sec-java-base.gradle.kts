import org.gradle.internal.impldep.org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace

plugins{
    java
    id("com.diffplug.spotless")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
group = "app.scircle.sec"
version = "0.0.1"

spotless{

    java{
        importOrder()
        removeUnusedImports()
        cleanthat()
        palantirJavaFormat("2.32.0").style("GOOGLE")
        formatAnnotations()
    }
}