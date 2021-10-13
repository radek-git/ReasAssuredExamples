package favourites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveImageAsFavouriteRequest {

    private String image_id;
    private String sub_id;
}
