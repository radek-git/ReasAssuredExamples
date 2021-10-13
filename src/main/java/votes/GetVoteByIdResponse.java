package votes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVoteByIdResponse {

    private int id;
    private String user_id;
    private String image_id;
    private String sub_id;
    private String created_at;
    private int value;
    private String country_code;
}
