package favourites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListOfFavouritesResponse {

    private int id;
    private String user_id;
    private String image_id;
    private String sub_id;
    private String created_at;
    private Image image;

}
