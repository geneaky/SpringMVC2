package hello.typeconverter.formatter;

import static org.assertj.core.api.Assertions.*;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

  @Test
  public void formattingConversionService() throws Exception{
    //given
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
    //컨버터 등록
    conversionService.addConverter(new StringToIpPortConverter());
    conversionService.addConverter(new IpPortToStringConverter());
    //포멧터 등록
    conversionService.addFormatter(new MyNumberFormatter());
    //컨버터 사용
    IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
    //포멧터 사용
    String convert = conversionService.convert(1000, String.class);
    Long convert1 = conversionService.convert("1,000", Long.class);
    //then
    assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
    assertThat(convert).isEqualTo("1,000");
    assertThat(convert1).isEqualTo(1000);
  }

}
