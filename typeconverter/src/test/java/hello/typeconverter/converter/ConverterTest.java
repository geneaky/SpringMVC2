package hello.typeconverter.converter;

import static org.assertj.core.api.Assertions.*;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterTest {

  @Test
  public void stirngToInteger() throws Exception{
    //given
    StringToIntegerConverter converter = new StringToIntegerConverter();
    //when
    Integer result = converter.convert("10");
    //then
    assertThat(result).isEqualTo(10);
  }

  @Test
  public void integerToString() throws Exception{
    //given
    IntegerToStringConverter converter = new IntegerToStringConverter();
    //when
    String result = converter.convert(10);
    //then
    assertThat(result).isEqualTo("10");
  }

  @Test
  public void stringToIpPort() throws Exception{
    //given
    IpPortToStringConverter converter = new IpPortToStringConverter();
    //when
    IpPort source = new IpPort("127.0.0.1", 8080);
    String result = converter.convert(source);
    //then
    assertThat(result).isEqualTo("127.0.0.1:8080");
  }
  
  @Test
  public void ipPOrtToString() throws Exception{
    //given
    StringToIpPortConverter converter = new StringToIpPortConverter();
    String source = "127.0.0.1:8080";
    //when
    IpPort result = converter.convert(source);
    //then
    assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
  }
}
