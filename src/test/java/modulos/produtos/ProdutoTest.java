package modulos.produtos;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Teste de API Rest do módulo de Produtos")
public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach(){
        //Configurando os dados da API Rest da lojinha
        baseURI = "http://165.227.93.41";
        //port = 8080;
        basePath = "/lojinha-bugada";

        UsuarioPojo usuario = new UsuarioPojo();
            usuario.setUsuarioLogin("admin");
            usuario.setUsuarioSenha("admin");

        //Obter o token do usuário admin
        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuario)
            .when()
                .post("/v2/login")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Validar que o valor 0.00 do produto não é válido")
    public void testValidarValorZeroProduto() {
        //Tentar inserir um poduto com valor 0.00 e validar que a mensagem de erro foi apresentada
        //e o status code retornado foi 422
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Televisão");
        produto.getProdutoValor(0.00);
        List<ComponentePojo>

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body("{\n" +
                        "  \"produtoNome\": \"Automation\",\n" +
                        "  \"produtoValor\": 0.00,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"Preta\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"\",\n" +
                        "      \"componenteQuantidade\":1\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Controle Remoto\",\n" +
                        "      \"componenteQuantidade\":1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .when()
                    .post("/v2/produtos")
                .then()
                    .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
    @Test
    @DisplayName("Validar que o valor 7000.01 do produto não é válido")
    public void testValidarValorSeteMilProduto(){
        //Tentar inserir um poduto com valor 0.00 e validar que a mensagem de erro foi apresentada
        //e o status code retornado foi 422
          given()
                  .contentType(ContentType.JSON)
                  .header("token", this.token)
                  .body("{\n" +
                        "  \"produtoNome\": \"Automation\",\n" +
                        "  \"produtoValor\": 7000.01,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"Preta\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"\",\n" +
                        "      \"componenteQuantidade\":1\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"Controle Remoto\",\n" +
                        "      \"componenteQuantidade\":1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                  .when()
                        .post("/v2/produtos")
                  .then()
                        .assertThat()
                            .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                            .statusCode(422);

        }


}
