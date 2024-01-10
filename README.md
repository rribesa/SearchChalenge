# Project README

## Objective (Work in Progress - WIP)
- Create a product search app

## V1 Features
- Online product search
- Record and consult online history
- Product details

## Project Architecture
The MVVM architecture was chosen for this project. For the ViewModel, a single State approach was adopted to control the screen, manage history, and handle results. The repository pattern is implemented to control between remote and local data sources. For use cases, a one-use case per function approach was chosen. I tried to apply Solid and Clean Architecture concepts as much as possible, deviating only in some aspects to simplify the project due to its relatively small size.

## Module Structure
- Infrastructure 
    - network
    - logger
- Search
    - public
    - impl
- App

## Selected Libraries
- Retrofit and Gson for the network layer and data parsing
- Jetpack Compose for UI component presentation, and Coil for image presentation
- Koin for dependency injection
- Coroutines and Flow for asynchronous communication
- Navigation for navigating between Compose components
- Data Store for saving simple data like history
- Room will be considered for future implementation for result caching

## Next Steps
- Replace Gson with Kotlin Serialization
- Move search module to features module
  
