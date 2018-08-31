package bms.kotlinplayground

object Model {

    //creating getter setter in one line, for variable query
    data class Result(val query: Query)

    //creating getter setter in one line, for variable searchinfo
    data class Query(val searchinfo: SearchInfo)

    //creating getter setter in one line, for variable totalhits
    data class SearchInfo(val totalhits: Int)

}