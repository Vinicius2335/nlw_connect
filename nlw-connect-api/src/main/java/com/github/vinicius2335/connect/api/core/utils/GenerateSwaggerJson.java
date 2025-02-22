package com.github.vinicius2335.connect.api.core.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Log4j2
public class GenerateSwaggerJson {
    private final RestTemplate restTemplate;

    public void execute(){
        // retorna o swagger gerado pela aplicação no formato json
        String swagger = this.restTemplate.getForObject(
                "http://localhost:8080/v3/api-docs",
                String.class
        );

        // transforma esse json num arquivo
        this.writeFile("swagger.json", swagger);
    }

    private void writeFile(String fileName, String content) {

        //File theDir = new File("swagger");
        //
        //// cria o diretório onde será salvo o swagger
        //if (!theDir.exists()) {
        //    try{
        //        theDir.mkdir();
        //    }
        //    catch(SecurityException ex){
        //        log.error(ex.getMessage());
        //    }
        //}

        // criando o arquivo swagger.json
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content);

            log.info("Generate swagger.json");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

    }
}
