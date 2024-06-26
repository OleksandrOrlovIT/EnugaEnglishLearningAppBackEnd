package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomPageRequest {

    @NotNull(message = "Page number cannot be null")
    @Min(value = 0, message = "Page number cannot be less then 0")
    private int page;

    @NotNull(message = "Page size cannot be null")
    @Min(value = 1, message = "Page size cannot be less then 0")
    private int size;

    public CustomPageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
