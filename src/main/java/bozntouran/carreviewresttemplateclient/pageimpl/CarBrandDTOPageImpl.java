package bozntouran.carreviewresttemplateclient.pageimpl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true , value = "pageable")
public class CarBrandDTOPageImpl<CarBrandDTO> extends PageImpl<CarBrandDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarBrandDTOPageImpl(@JsonProperty("content") List<CarBrandDTO> content,
                               @JsonProperty("number") int page,
                               @JsonProperty("size") int size,
                               @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public CarBrandDTOPageImpl(List<CarBrandDTO> content) {
        super(content);
    }
}
