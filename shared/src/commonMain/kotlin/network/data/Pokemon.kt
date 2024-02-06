package network.data

@kotlinx.serialization.Serializable
data class Pokemon (
    val id:Int,
    val name:String,
    val types:List<Type>,
    val height:Float,
    val weight:Float,
    val stats:List<Stats>,
    val abilities:List<Ability>,
    val moves:List<Move>
)

@kotlinx.serialization.Serializable
data class Stats (
    val base_stat:Int,
    val stat:Stat
)

@kotlinx.serialization.Serializable
data class Stat (
    val name:String,
)

@kotlinx.serialization.Serializable
data class Ability (
    val ability:AbilityName,
    val is_hidden:Boolean
)

@kotlinx.serialization.Serializable
data class AbilityName (
    val name:String
)

@kotlinx.serialization.Serializable
data class Move (
    val move:MoveName
)

@kotlinx.serialization.Serializable
data class MoveName (
    val name:String
)

@kotlinx.serialization.Serializable
data class Type (
    val type:TypeName
)

@kotlinx.serialization.Serializable
data class TypeName (
    val name:String
)

@kotlinx.serialization.Serializable
data class Limit (
    val results: List<LimitRes>
)

@kotlinx.serialization.Serializable
data class LimitRes (
    val name:String
)