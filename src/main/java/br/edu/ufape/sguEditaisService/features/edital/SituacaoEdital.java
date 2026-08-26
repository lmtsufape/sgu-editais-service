package br.edu.ufape.sguEditaisService.features.edital;

public enum SituacaoEdital {
    PLANEJAMENTO, // Sendo construído, pode sofrer Cópia Profunda (Deep Copy)
    PUBLICADO,    // Aberto para o público. Congela a estrutura.
    FINALIZADO,   // Prazos encerrados.
    CANCELADO     // Revogado.
}