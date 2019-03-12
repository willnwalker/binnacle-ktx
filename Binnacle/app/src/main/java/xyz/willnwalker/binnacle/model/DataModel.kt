package xyz.willnwalker.binnacle.model

enum class SensorType{
    Phone,
    Bluetooth
}

abstract class DataModelBase{
    abstract var currentBoat: Boat
    abstract var idealBoat: Boat
    abstract var wind: Wind
}

class DataModel: DataModelBase(){
    init{
        
    }
}