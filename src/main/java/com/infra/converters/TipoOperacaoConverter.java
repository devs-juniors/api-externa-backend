package com.infra.converters;

import com.domains.enums.TipoOperacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoOperacaoConverter implements AttributeConverter<TipoOperacao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoOperacao tipoOperacao) {
        return tipoOperacao == null ? null : tipoOperacao.getId();
    }

    @Override
    public TipoOperacao convertToEntityAttribute(Integer dbValue) {
        return TipoOperacao.toEnum(dbValue);
    }
}
