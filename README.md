# PennStation   [ ![Download](https://api.bintray.com/packages/edisonw/android/PennStation/images/download.svg) ](https://bintray.com/edisonw/android/PennStation/_latestVersion)

[PennStation] is an event service that I use as a template to build apps that require IPC.

# Why?

[EventBus] is a great communication pattern on apps and have great library adoptions such as [EventBus by greenrobot] and [Otto by Square] on Android. 

However, as applications scale, you write too many boilerplate code, code gets refactored but not cleaned up, and in the rare case do we want inter-process communication, Event Bus pattern falls short.

PennStation is an inter-process event service library that supports the following usage pattern:

 * UI process/thread sends a request to a remote service. 
 * Remote service process the request and sends the result back to the requesting process. (can be extended to broadcast to all bounded processes)
 * Requesting process broadcast the event to all listeners in order. 
 * Bundle parceling is enforced cross process boundaries.

And during this entire process....write only the [implementation code for the action] and the [event listeners].
 
# Usage

I'll add them to jcenter soon..but for now: 

* Download and merge the following to your gradle file: 

```gradle
buildscript {
    repositories {
        jcenter()
        maven {
            url 'http://dl.bintray.com/edisonw/android'
        }
    }
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
apply plugin: 'com.neenbedankt.android-apt'
apt {
    processor "com.edisonwang.ps.processors.PennStationProcessor"
}
dependencies {
    apt 'com.edisonwang.ps:ps_processors:1.0.3'
    compile 'com.edisonwang.ps:ps_lib:1.0.3'
}
```

* Add the following to your AndroidManifest.xml (or extend a new service class), you can also add a :process tag to make it run on a separate process.
```xml
        <service
            android:name="com.edisonwang.ps.lib.EventService" />
```
* In your custom Application class or Activity.onCreate(), add 
```java
        PennStation.init(getApplication(), EventService.class); //or extended class.
```

For each action the service needs to perform: 

* Write the actions you want the event service to take. ([Full SampleAction])
* Tag it with @EventProducer with the events it will emit.
* Tag it with @RequestFactory so it will be registered.
* Optional: Tag it with @RequestFactoryWithVariables so convenience factories will be created.
* Optional: Tag it with @RequestFactoryWithClass so convenience methods will be added.

For class that owns [event listeners]:

* Write the listeners that listens to those events and XXXEventListener will be generated.
* Implement the listeners and (un)register it via Events.registerListener().

[PennStation]: https://github.com/edisonw/Ipes
[EventBus]: https://github.com/google/guava/wiki/EventBusExplained
[Otto by Square]: http://square.github.io/otto/
[EventBus by greenrobot]: https://github.com/greenrobot/
[implementation code for the action]: https://github.com/edisonw/PennStation/blob/master/sample-app/src/main/java/com/edisonwang/ps/sample/SimpleAction.java
[event listeners]: https://github.com/edisonw/PennStation/blob/master/sample-app/src/main/java/com/edisonwang/ps/sample/SampleActivity.java
[Full SampleAction]: https://github.com/edisonw/PennStation/blob/master/sample-app/src/main/java/com/edisonwang/ps/sample/ComplicatedAction.java
