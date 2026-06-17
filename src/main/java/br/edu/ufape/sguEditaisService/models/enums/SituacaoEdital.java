package br.edu.ufape.sguEditaisService.models.enums;

public enum SituacaoEdital {
    PLANEJAMENTO, // O edital está sendo montado, as datas podem estar nulas ou sendo alteradas. Invisível para os candidatos.
    PUBLICADO,    // Lançado oficialmente. Passa a ser visível para inscrição. Congela alterações drásticas de estrutura.
    FINALIZADO,   // Processo encerrado.
    CANCELADO     // Edital revogado.
}