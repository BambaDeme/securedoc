package deme.ahmadou.securedoc.enums.converter;

import deme.ahmadou.securedoc.enums.Authority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Authority, String> {
    @Override
    public String convertToDatabaseColumn(Authority authority) {
        if(authority == null){
            return null;
        } else {
            return authority.getValue();
        }
    }

    @Override
    public Authority convertToEntityAttribute(String code) {

        if(code == null){
            return null;
        } else {
            return Stream.of(Authority.values())
                    .filter(authority -> authority.getValue().equals(code))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}
