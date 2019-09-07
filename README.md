# libgdx-2d-engine

A small 2D game engine with many utilities, more like a framework

[![Build Status](https://travis-ci.org/JuKu/libgdx-2d-engine.svg?branch=master)](https://travis-ci.org/JuKu/libgdx-2d-engine)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=ncloc)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=coverage)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Technical Debt Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=sqale_index)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=code_smells)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=bugs)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=vulnerabilities)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.jukusoft%3Aengine2d-parent&metric=security_rating)](https://sonarcloud.io/dashboard/index/com.jukusoft%3Aengine2d-parent) 

[![Sonarcloud](https://sonarcloud.io/api/project_badges/quality_gate?project=com.jukusoft%3Aengine2d-parent)](https://sonarcloud.io/dashboard?id=com.jukusoft%3Aengine2d-parent)

# Deployment
Build with `mvn clean install deploy -DperformRelease=true` to use maven-gpg-plugin

# Basic Game Config

Set property for DesktopLauncher in pom.xml:
```xml
<properties>
    <!-- important! Don't override this, if you don't know, what you do! -->
    <mainClass>com.jukusoft.engine2d.desktop.DesktopLauncher</mainClass>
</properties>
```

```xml
<!-- ... -->

    <build>
		<plugins>
			<!-- Write the current git revision into ${buildnumber} and populate ${scmBranch} -->
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>buildnumber-maven-plugin</artifactId>
				<version>1.4</version>
				<executions>
					<execution>
						<goals>
							<goal>create</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<!-- Get the scm revision once for all modules -->
					<getRevisionOnlyOnce>true</getRevisionOnlyOnce>
					<!-- Don't fail on modified local resources -->
					<doCheck>false</doCheck>
					<!-- Don't update SCM -->
					<doUpdate>false</doUpdate>
					<!-- Use short version of git revision -->
					<shortRevisionLength>7</shortRevisionLength>
				</configuration>
			</plugin>

			<!-- create jar artifact, add classpath and set main class -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>3.0.2</version>
				<configuration>
					<archive>
						<manifest>
							<addClasspath>true</addClasspath>
							<classpathPrefix>lib/</classpathPrefix>
							<mainClass>${mainClass}</mainClass>

							<!-- update dependencie versions in MANIFEST.MF -->
							<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
							<addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
						</manifest>
						<manifestEntries>
							<!-- https://www.youtube.com/watch?v=Rnmq_Jv-pe4 -->
							<!-- http://www.javacreed.com/how-to-add-splash-screen-using-maven/ -->
							<SplashScreen-Image>splash_screen.png</SplashScreen-Image>

							<!-- http://maven.apache.org/shared/maven-archiver/index.html -->

							<!-- <key>value</key> -->
							<Implementation-Time>${maven.build.timestamp}</Implementation-Time>

							<Implementation-Build>${buildNumber}-${scmBranch}</Implementation-Build>
						</manifestEntries>
					</archive>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

Next you have to create an `BaseGameFactory` implementation, e.q.:
```java
public class TestBaseGameFactory implements BaseGameFactory {

    @Override
    public BaseGame createGame() {
        return new BaseGame(TestBaseGameFactory.class) {
            @Override
            protected void addSubSystems(SubSystemManager manager) {
                //
            }
        };
    }

}
```

Then you have to register this factory implementation via SPI.\
Create an directory "META-INF/services" in resources directory (e.q. src/main/resources/META-INF/service) and create a new file with the exact file name "com.jukusoft.engine2d.applayer.BaseGameFactory" (without extra extension) there.\
File content should be your package and class name of your factory implementation, e.q.:
```java
com.jukusoft.engine2d.test.desktop.TestBaseGameFactory
```
