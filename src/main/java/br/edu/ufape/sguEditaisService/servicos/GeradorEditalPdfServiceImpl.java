//package br.edu.ufape.sguEditaisService.servicos;
//
//import br.edu.ufape.sguEditaisService.models.Documento;
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.GeradorEditalPdfService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Slf4j
//@Service
//public class GeradorEditalPdfServiceImpl implements GeradorEditalPdfService {
//
//    @Override
//    public Documento gerarESalvarPdf(Edital edital) {
//        log.info("Simulando a geração de PDF para o Edital: {}", edital.getTitulo());
//
//        // No futuro, aqui entrará a lógica real de conversão de HTML para PDF.
//        // Por enquanto, devolvemos um documento falso para não quebrar a Fachada.
//
//        Documento documento = new Documento();
//        documento.setNome("edital_gerado_automaticamente.pdf");
//        documento.setPath("/tmp/fake_pdfs/" + System.currentTimeMillis() + ".pdf");
//
//        return documento;
//    }
//}