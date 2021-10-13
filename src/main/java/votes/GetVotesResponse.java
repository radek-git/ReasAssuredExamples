package votes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVotesResponse {

    private int id;
    private String image_id;
    private String sub_id;
    private String value;


}
