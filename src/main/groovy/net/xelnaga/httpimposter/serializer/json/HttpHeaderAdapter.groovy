package net.xelnaga.httpimposter.serializer.json

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.HttpHeader

class HttpHeaderAdapter extends TypeAdapter<HttpHeader> {

    public HttpHeader read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        Map params = generateParams(reader)
        validateParams(params)
        return new DefaultHttpHeader(params.name, params.value)
    }

    private Map generateParams(JsonReader reader) {
        Map params = [:]

        reader.beginObject()

        addNextParam(reader, params)
        addNextParam(reader, params)

        reader.endObject()

        return params
    }

    private void addNextParam(JsonReader reader, Map params) {
        String elementName = reader.nextName()
        String elementValue = reader.nextString()
        params[elementName] = elementValue
    }

    private void validateParams(Map params) {
        if (!params.keySet().containsAll(['name', 'value'])) {
            throw new IOException("expected name and value keys, keys: '${params}'")
        }
    }

    public void write(JsonWriter writer, HttpHeader header) throws IOException {
        if (header == null) {
            writer.nullValue()
            return
        }
        writer.beginObject()
        writer.name('name')
        writer.value(header.name)
        writer.name('value')
        writer.value(header.value)
        writer.endObject()
    }
}
