//package br.edu.ufape.sguEditaisService.servicos.interfaces;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
//import br.edu.ufape.sguEditaisService.models.Documento;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//public interface ArmazenamentoService {
//    @Transactional
//    List<Documento> salvarArquivo(MultipartFile[] arquivos);
//
//    List<DocumentoResponse> converterDocumentosParaBase64(List<Documento> documentos) throws IOException;
//
//    // Método bônus para manter o servidor limpo quando apagarmos inscrições/editais
//    void apagarArquivoFisico(String path);
//}