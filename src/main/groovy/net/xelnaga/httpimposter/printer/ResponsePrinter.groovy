package net.xelnaga.httpimposter.printer

import net.xelnaga.httpimposter.model.ResponsePreset
import net.xelnaga.httpimposter.model.DefaultHttpHeader

class ResponsePrinter {

    String print(ResponsePreset response) {

        String output = response.status + '\n'

        if (response.headers.size() > 0) {
            output += '\n'
            response.headers.each { DefaultHttpHeader header ->
                output += header.name.toLowerCase() + ': ' + header.value + '\n'
            }
        }

        if (response.body.size() > 0) {
            output += '\n'
            output += response.body + '\n'
        }

        return output
    }
}
