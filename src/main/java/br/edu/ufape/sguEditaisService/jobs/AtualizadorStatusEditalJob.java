//package br.edu.ufape.sguEditaisService.jobs;
//
//import br.edu.ufape.sguEditaisService.models.DataEtapa;
//import br.edu.ufape.sguEditaisService.models.Edital;
//import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
//import br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class AtualizadorStatusEditalJob {
//
//    private final EditalService editalService;
//
//    // Roda todos os dias às 00:01:00 (Meia noite e um minuto)
//    @Scheduled(cron = "0 1 0 * * *")
//    @Transactional
//    public void atualizarStatusDosEditais() {
//        log.info("Iniciando o Robô de atualização de status dos editais...");
//
//        LocalDateTime agora = LocalDateTime.now();
//        List<Edital> editais = editalService.listar(); // Pode otimizar depois para buscar apenas os 'Ativos'
//
//        for (Edital edital : editais) {
//            if (!edital.isAtivo()) continue;
//
//            // 1. Se o edital já passou da data fim geral, desativa-o ou marca como concluído
//            if (agora.isAfter(edital.getDataFim())) {
//                edital.setAtivo(false);
//                editalService.salvar(edital);
//                log.info("Edital ID {} foi marcado como inativo (Prazo expirado).", edital.getId());
//                continue;
//            }
//
//            // 2. Procura qual é a Etapa do Cronograma que está a acontecer AGORA
//            Optional<DataEtapa> etapaAtual = edital.getCronograma().stream()
//                    .filter(d -> !agora.isBefore(d.getDataInicio()) && !agora.isAfter(d.getDataFim()))
//                    .findFirst();
//
//            // 3. Se houver uma etapa ativa, o Edital "herda" o status dessa etapa!
//            if (etapaAtual.isPresent()) {
//                StatusPersonalizado novoStatus = etapaAtual.get().getStatus();
//                StatusPersonalizado statusAtual = edital.getStatus();
//
//                // Se o status for diferente do atual, atualizamos!
//                if (statusAtual == null || !statusAtual.getId().equals(novoStatus.getId())) {
//                    edital.setStatus(novoStatus);
//                    editalService.salvar(edital);
//                    log.info("Status do Edital ID {} atualizado automaticamente para: {}", edital.getId(), novoStatus.getNome());
//                }
//            }
//        }
//
//        log.info("Robô de atualização finalizado.");
//    }
//}