## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).



public class App {
    static String[] cores = {
        "Preto", "Marrom", "Vermelho", "Laranja", "Amarelo", "Verde", "Azul", "Violeta", "Cinza", "Branco"
    };

    public static String[] obterCoresResistor(String resistenciaString) {
        String[] coresResistor = new String[4];
        double resistencia = 0;

        // Separar valor e unidade
        String[] partes = resistenciaString.split(" ");
        if (partes.length < 2) {
            System.out.println("Formato inválido para: " + resistenciaString);
            return new String[]{"Erro", "Erro", "Erro", "Erro"};
        }

        String valorString = partes[0];
        String unidade = partes[1];
        

        // Converter valor para ohms
        try {
            if (unidade.equals("ohms")) {
                resistencia = parseValorComUnidade(valorString);
            } else if (unidade.equals("k ohms")) {
                
                resistencia = parseValorComUnidade(valorString) * 1000;
            } else if (unidade.equals("M ohms")) {
                resistencia = parseValorComUnidade(valorString) * 1000000;
            } else {
                System.out.println("Unidade desconhecida para: " + resistenciaString);
                return new String[]{"Erro", "Erro", "Erro", "Erro"};
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro ao processar a resistência: " + resistenciaString);
            e.printStackTrace();
            return new String[]{"Erro", "Erro", "Erro", "Erro"};
        }

        // Ajustar o cálculo do multiplicador e dos dígitos
        int multiplicadorIndex = 0;
        if (resistencia >= 1000) {
            multiplicadorIndex = (int) (Math.log10(resistencia) - 2); // Ajustar o multiplicador para k ohms e M ohms
        } else if (resistencia >= 100) {
            multiplicadorIndex = (int) (Math.log10(resistencia) - 1); // Ajustar o multiplicador para ohms
        } else {
            multiplicadorIndex = 0; // Para resistências menores que 100 ohms
        }

        int valorBase = (int) (resistencia / Math.pow(10, multiplicadorIndex));
        int digito1 = valorBase / 10;
        int digito2 = valorBase % 10;

        // Verificar se os índices estão dentro dos limites do array cores
        if (digito1 < 0 || digito1 >= cores.length) digito1 = 0;
        if (digito2 < 0 || digito2 >= cores.length) digito2 = 0;
        if (multiplicadorIndex < 0 || multiplicadorIndex >= cores.length) multiplicadorIndex = 0;

        // Definir as cores dos dígitos e do multiplicador
        coresResistor[0] = cores[digito1]; // Primeira banda
        coresResistor[1] = cores[digito2];  // Segunda banda
        coresResistor[2] = cores[multiplicadorIndex]; // Multiplicador

        // Definir a cor da tolerância (assumindo 5% de tolerância)
        coresResistor[3] = "Dourada";

        return coresResistor;
    }

    private static double parseValorComUnidade(String valorString) throws NumberFormatException {
        if (valorString.endsWith("k")) {
            return Double.parseDouble(valorString.substring(0, valorString.length() - 1));
        } else if (valorString.endsWith("M")) {
            return Double.parseDouble(valorString.substring(0, valorString.length() - 1));
        } else {
            return Double.parseDouble(valorString);
        }
    }

    public static void main(String[] args) {
        // Testar com diferentes valores de resistência
        String[] resistencias = {"1k ohms"};

        for (String resistencia : resistencias) {
            String[] coresResistor = obterCoresResistor(resistencia);
            System.out.println("As cores do resistor " + resistencia + " são: " + String.join(", ", coresResistor));
        }
    }
}
