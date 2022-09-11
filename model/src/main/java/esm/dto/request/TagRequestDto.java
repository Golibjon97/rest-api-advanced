package esm.dto.request;

import javax.validation.constraints.NotBlank;
public class TagRequestDto {

    @NotBlank(message = "name can not be empty")
    private String name;

    public TagRequestDto(String name) {
        this.name = name;
    }

    public TagRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
