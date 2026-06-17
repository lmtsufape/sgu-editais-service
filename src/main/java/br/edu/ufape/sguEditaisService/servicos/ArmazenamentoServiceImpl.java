//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
//import br.edu.ufape.sguEditaisService.models.Documento;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.ArmazenamentoService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ArmazenamentoServiceImpl implements ArmazenamentoService {
//
//    private final List<String> tiposPermitidos = List.of("application/pdf", "image/jpeg", "image/png", "image/jpg");
//
//    @Value("${arquivo.diretorio-upload:uploads/editais/}") // Adicionado um valor default por segurança
//    private String uploadDir;
//
//    @Transactional
//    @Override
//    public List<Documento> salvarArquivo(MultipartFile[] arquivos) {
//        List<Documento> documentosSalvos = new ArrayList<>();
//
//        // Garante que a pasta existe antes de tentar salvar
//        Path dirPath = Paths.get(uploadDir);
//        if (!Files.exists(dirPath)) {
//            try {
//                Files.createDirectories(dirPath);
//            } catch (IOException e) {
//                throw new RuntimeException("Não foi possível criar o diretório de uploads.", e);
//            }
//        }
//
//        for (MultipartFile arquivo : arquivos) {
//            if (arquivo == null || arquivo.isEmpty()
//                    || arquivo.getOriginalFilename() == null
//                    || arquivo.getOriginalFilename().isBlank()
//                    || arquivo.getContentType() == null
//            ) {
//                throw new IllegalArgumentException("Um dos arquivos está vazio ou não foi selecionado corretamente");
//            }
//            if (!tiposPermitidos.contains(arquivo.getContentType())) {
//                throw new IllegalArgumentException("Tipo de arquivo não permitido!");
//            }
//
//            String uuid = UUID.randomUUID().toString();
//            String extensao = FilenameUtils.getExtension(arquivo.getOriginalFilename());
//            String nomeArquivoComUUID = uuid + "." + extensao;
//
//            try {
//                Path caminho = Paths.get(uploadDir, nomeArquivoComUUID);
//                Files.copy(arquivo.getInputStream(), caminho);
//
//                Documento documento = new Documento();
//                documento.setNome(nomeArquivoComUUID);
//                documento.setPath(caminho.toString());
//                documentosSalvos.add(documento);
//            } catch (IOException e) {
//                throw new RuntimeException("Falha ao salvar arquivo!");
//            }
//        }
//        return documentosSalvos;
//    }
//
//    @Override
//    public List<DocumentoResponse> converterDocumentosParaBase64(List<Documento> documentos) throws IOException {
//        List<DocumentoResponse> documentosBase64 = new ArrayList<>();
//        for (Documento documento : documentos) {
//            // Ajuste fino: Se o path do banco já contiver o uploadDir, evitamos duplicar
//            Path filePath = Paths.get(documento.getPath()).normalize();
//
//            if (Files.exists(filePath)) {
//                byte[] fileBytes = Files.readAllBytes(filePath);
//                String base64 = Base64.getEncoder().encodeToString(fileBytes);
//                // Use o construtor correto do seu DocumentoResponse
//                documentosBase64.add(new DocumentoResponse(documento.getNome(), base64));
//            }
//        }
//        return documentosBase64;
//    }
//
//    @Override
//    public void apagarArquivoFisico(String path) {
//        try {
//            Path fileToDeletePath = Paths.get(path);
//            Files.deleteIfExists(fileToDeletePath);
//        } catch (IOException e) {
//            log.error("Erro ao tentar apagar o arquivo físico no caminho: " + path, e);
//        }
//    }
//}