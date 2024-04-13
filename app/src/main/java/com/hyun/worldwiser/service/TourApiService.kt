import com.hyun.worldwiser.model.spots.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TourApiService {
    @GET("/B551011/KorService1/searchKeyword1")
    fun getTourSpots(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("MobileOS") mobileOS: String,
        @Query("MobileApp") mobileApp: String,
        @Query("_type") type: String = "json",
        @Query("listYN") listYN: String,
        @Query("arrange") arrange: String,
        @Query("keyword") keyword: String,
        @Query("contentTypeId") contentTypeId: Int
    ): Call<Root>
}