package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.models.Documento;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "caminho é obrigatório")
    private String caminho;
    private java.time.LocalDateTime dataUpload;
    private EtapaRequest etapa;
    private InscricaoRequest inscricao;

    public Documento convertToEntity(DocumentoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Documento.class);
    }
}
