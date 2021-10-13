package breeds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListOfBreedsResponse {

    private Weight weight;
    private Height height;
    private int id;
    private String name;
    private String bred_for;
    private String breed_group;
    private String life_span;
    private String temperament;
    private String origin;
    private String reference_image_id;
    private Image image;




}
