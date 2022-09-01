package work.gaigeshen.spring.security.demo.commons.json;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author gaigeshen
 * @see <a href="http://www.json.org/json-en.html">JSON (JavaScript Object Notation)</a>
 */
public interface JsonCodec {

  static JsonCodec instance() {
    return JacksonJsonCodec.INSTANCE;
  }

  String encode(Object obj);

  <T> T decode(String json, Class<T> resultClass);

  Map<String, Object> decodeObject(String json);

  Collection<Object> decodeArray(String json);

}
