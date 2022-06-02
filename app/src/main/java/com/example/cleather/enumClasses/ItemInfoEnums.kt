package com.example.cleather.enumClasses

//ItemType tells you what type of item it is. Feel free to write more examples
//in the comments
enum class ItemType {
    CLOTHING_UNDERWEAR, //"Layer 1" of clothing. Underwear clothing. Ex. socks, boxer shorts, bras, t-shirts
    CLOTHING,           //"Layer 2" of clothing. Normal Clothing. Ex. jumpers, hoodies, pants, shorts
    CLOTHING_SHELL,     //"Layer 3" of clothing. Clothes like jackets, skipants, coveralls
    ACCESSORIES,        //Accessories. Ex. scarfs, gloves, sunglasses, hats
    CONSUMABLES         //Consumables. Ex. sunscreen, ski lube (?),
}

//ItemWarmness tells you how much heat the clothing or the accessories retains.
//I've chosen to model it like this as it will indirectly solve our question on
//"What is warm?". The user will have one of these values to choose from when
//adding a piece of clothing and that way we don't have to think about what is
//"warm" for the user. This might be too rough of a model though, discussion is
//needed. Feel free to write more examples in the comments
enum class ItemWarmness {
    NA {override fun value(): Double? = null},       //If warmness value does not apply to item. Ex. sunscreen, ski lube
    WARM {override fun value(): Double = 20.0},        //Warm clothing or accessories. Down Jackets, scarfs, wool clothing....
    TEPIDWARM {override fun value(): Double = 15.0},
    TEPID {override fun value(): Double = 10.0},        //Medium warm clothing. Jumper, hoodie...
    TEPIDCOLD {override fun value(): Double = 5.0},
    COLD {override fun value(): Double = 0.0},           //Not warm clothing. T-shirt, shorts...
    FREEZING{override fun value(): Double = -5.0};

    abstract fun value(): Double?
}

//ItemSpecialFeature tells you about features the item has, that might not be
//come trough the other item descriptors. Ex. how would you know if the jacket
//is waterproof? Are we supposed to just roll the dice and hope?
enum class ItemSpecialFeature {
    NA, //Doesn't have a special feature to speak of
    WATERPROOF, //Is waterproof
    WINDPROOF, //Is windproof
    UV_PROTECTANT,
}

//ItemBodyPart tells you about where the clothing/accessories goes on your body.
//Idk would be strange if we only suggested upper body stuff. Feel free to write
//more examples in the comments
enum class ItemBodyPart {
    NA,
    UPPER_BODY,
    LOWER_BODY,
    FACE,
    NECK,
    ARMS,
    HANDS,
    FEET
}