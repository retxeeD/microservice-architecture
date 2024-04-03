package ms.studies.orchestrator.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PersonRequest {

    private String document;

    private String name;
}
