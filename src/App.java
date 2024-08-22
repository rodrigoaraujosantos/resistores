public class App {
    static String[] cores = {
        "Preto", "Marrom", "Vermelho", "Laranja", "Amarelo", "Verde", "Azul", "Violeta", "Cinza", "Branco"
    };

    public static String[] obterCoresResistor(String resistenciaString) {
        String[] coresResistor = new String[4];
        double resistencia = 0;

        
        String[] partes = resistenciaString.split(" ");
        if (partes.length < 2) {
            System.out.println("Formato inválido para: " + resistenciaString);
            return new String[]{"Erro", "Erro", "Erro", "Erro"};
        }

        String valorString = partes[0];
        String unidade = partes[1];
        

        
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

        
        int multiplicadorIndex = 0;
        if (resistencia >= 1000) {
            multiplicadorIndex = (int) (Math.log10(resistencia) - 2);
        } else if (resistencia >= 100) {
            multiplicadorIndex = (int) (Math.log10(resistencia) - 1);
        } else {
            multiplicadorIndex = 0;
        }

        int valorBase = (int) (resistencia / Math.pow(10, multiplicadorIndex));
        int digito1 = valorBase / 10;
        int digito2 = valorBase % 10;

        
        if (digito1 < 0 || digito1 >= cores.length) digito1 = 0;
        if (digito2 < 0 || digito2 >= cores.length) digito2 = 0;
        if (multiplicadorIndex < 0 || multiplicadorIndex >= cores.length) multiplicadorIndex = 0;

        
        coresResistor[0] = cores[digito1];
        coresResistor[1] = cores[digito2];
        coresResistor[2] = cores[multiplicadorIndex];

        
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
        
        String[] resistencias = {"10 ohms", "100 ohms", "220 ohms", "330 ohms", "470 ohms", "680 ohms", "1k ohms", "2M ohms"};

        for (String resistencia : resistencias) {
            String[] coresResistor = obterCoresResistor(resistencia);
            System.out.println("As cores do resistor " + resistencia + " são: " + String.join(", ", coresResistor));
        }
    }
}
