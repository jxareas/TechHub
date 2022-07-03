# TechHub

TechHub is a sample educational app that provides courses for people who want to learn new skills in mostly tech-related areas.

The goal of this project is to practice & demonstrate the use of some Modern Android Development Practices,
Material Design Theming & Android Animations.

## 🕹 Animations 🕹

This app uses shared element container transforms to create a visible connection between two UI elements and represent
parent-child relationships between fragment transitions.

List Item	|	Card Item	|	Swipe to Delete | Bouncing Text	|
:------:|:---------------------:|:-----------------------------:|:-------------:|
![](https://github.com/jxareas/TechHub/blob/master/assets/animations/anim_list_item.gif?raw=true)  |  ![](https://github.com/jxareas/TechHub/blob/master/assets/animations/anim_card_item.gif?raw=true)  |  ![](https://github.com/jxareas/TechHub/blob/master/assets/animations/anim_swipe.gif?raw=true)  |  ![](https://github.com/jxareas/TechHub/blob/master/assets/animations/anim_text.gif?raw=true)


## 🛠 Architecture 🛠

The application complies with the following recommended best practices for building robust, high-quality apps.

* **Single Activity Architecture**: One single activity and several fragments.
* **MVVM**: MVVM based architecture, using a local database as a Single Source of Truth.
* **SOLID**: Software design principles intended to make object-oriented software more understandable, flexible and maintainable.
* **Android Architecture Components**:  libraries part of Android Jetpack to give a robust design, testable and maintainable to modern applications.

![](https://camo.githubusercontent.com/e1459518188f17c1fa6a30570ca5d21530975f9e/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f746f7069632f6c69627261726965732f6172636869746563747572652f696d616765732f66696e616c2d6172636869746563747572652e706e67)

## 🦾 Tech Stack 🦾

This project takes advantage of many popular libraries and tools in the Android ecosystem:

* **ROOM**: Persistence Library that provides an abstraction layer over SQLLite to allow for a more robust database access.
* **Lifecycle**: Perform actions in response to a change in the lifecycle status of another component.
* **ViewModel**: Designed to manage and store UI related data in a lifecycle conscious way.
* **LiveData**: A lifecycle-aware observable data holder class, that respects the lifecycle of other app components.
* **Navigation**: Implement navigation and transactions between fragments.
* **Kotlin Coroutines**: Managing background threads with simplified code and reducing needs for callbacks.
* **Kotlin Flow**: Cold asynchronous data stream that sequentially emits values
* **Kotlin Serialization**: Converts data used by an application to a format that can be transferred over a network or stored in a database or a file.
* **Dagger Hilt**: A fully static, compile-time dependency injection framework for both Java and Android.
* **Retrofit**: A type-safe HTTP client.
* **Glide**: An image loading library.
* **ViewBinding**: Allows you to more easily write code that interacts with views.
* **Material 3**: The latest version of Material Design, introduced in Android 12.
* **Material Motion**: A set of transition patterns that can help users understand and navigate an app.

## 📷 Screenshots 📷

| Details | Home |  Favorites | Topics
|:-:|:-:|:-:|:-:|
| ![1](./assets/screenshots/course_light.png) | ![2](./assets/screenshots/home_light.png) | ![3](./assets/screenshots/favorites_light.png) | ![4](./assets/screenshots/topics_light.png)
| Details Dark | Home Dark |  Favorites Dark | Topics Dark
| ![5](./assets/screenshots/course_dark.png) | ![6](./assets/screenshots/home_dark.png) | ![7](./assets/screenshots/favorites_dark.png) | ![8](./assets/screenshots/topics_dark.png)




## License
```
MIT License

Copyright (c) 2022 Jonathan Areas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
