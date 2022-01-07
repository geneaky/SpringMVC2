package hello.typeconverter.formatter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MyNumberFormatterTest {

  MyNumberFormatter formatter = new MyNumberFormatter();

  @Test
  public void parse() throws Exception{
    //given
    Number result = formatter.parse("1,000", Locale.KOREA);
    //when
    //then
    assertThat(result).isEqualTo(1000L);
  }

  @Test
  public void print() throws Exception{
    //given
    String result = formatter.print(1000, Locale.KOREA);
    //when
    //then
    assertThat(result).isEqualTo("1,000");
  }

}