package xyz.willnwalker.binnacle.model

enum class SensorType{
    Phone,
    Bluetooth
}

abstract class DataModelBase{
    lateinit var currentBoat: Boat
    lateinit var idealBoat: Boat
    lateinit var wind: Wind
}

class DataModel(sensorType: SensorType): DataModelBase(){
    init{
        when(sensorType){
            SensorType.Phone ->{

            }
            SensorType.Bluetooth->{

            }
        }
    }
}