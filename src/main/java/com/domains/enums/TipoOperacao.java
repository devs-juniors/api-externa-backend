package com.domains.enums;

public enum TipoOperacao {
    COMPRA(1, "COMPRA"),
    VENDA(2, "VENDA");

    private Integer id;
    private String descricao;

    TipoOperacao(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static TipoOperacao toEnum(Integer id) {
        if (id == null) return null;
        for (TipoOperacao tipo : TipoOperacao.values()) {
            if (id.equals(tipo.getId())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de operação inválido: " + id);
    }
}
