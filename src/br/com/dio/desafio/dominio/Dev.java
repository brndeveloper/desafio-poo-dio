package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {
    private String nome;
    private Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();
    private int metaHorasPorDia = 0;

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevsInscritos().add(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();
        if(conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
        } else {
            System.err.println("Você não está matriculado em nenhum conteúdo!");
        }
    }

    public double calcularTotalXp() {
        Iterator<Conteudo> iterator = this.conteudosConcluidos.iterator();
        double soma = 0;
        while(iterator.hasNext()){
            double next = iterator.next().calcularXp();
            soma += next;
        }
        return soma;

        /*return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();*/
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public void setConteudosInscritos(Set<Conteudo> conteudosInscritos) {
        this.conteudosInscritos = conteudosInscritos;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void setConteudosConcluidos(Set<Conteudo> conteudosConcluidos) {
        this.conteudosConcluidos = conteudosConcluidos;
    }

    public int getMetaHorasPorDia() {
        return metaHorasPorDia;
    }

    public void setMetaHorasPorDia(int metaHorasPorDia) {
        this.metaHorasPorDia = metaHorasPorDia;
    }

    private int calcularTotalHorasCursos() {
        int totalHoras = 0;

        // fazendo um flag para mandar para estimarDiasParaConcluir para dizer se nao tem conteudoInscrito
        if (conteudosInscritos.isEmpty()) {
            return -1;
        }
        // aqui é um foreach simples mesmo para iterar sobre todos os conteúdos inscritos
        for (Conteudo conteudo : conteudosInscritos) {
            // aqui a gente vai verificar se o conteúdo é do curso, para não lançar exceção nem nada
            if (conteudo instanceof Curso) {
                // se for do curso, adiciona a carga horaria no total de horas
                totalHoras += ((Curso) conteudo).getCargaHoraria();
            }
        }
        return totalHoras;
    }

    public void estimarDiasParaConcluir() {
        // se a flag de lá der verdadeira, já encerra aqui mesmo, dizendo que não tem conteudoInscrito
        if (calcularTotalHorasCursos() == -1) {
            System.out.println("Você não está matriculado em nenhum conteúdo!");
            return;
        }
        // aqui vai calcular o total de horas de todos os cursos inscritos
        int totalHorasCurso = calcularTotalHorasCursos();

        // calcula o número de dias necessários para concluir os cursos
        // dividindo o total de horas pelo número de horas e arredondando para cima
        int dias = (int) Math.ceil((double) totalHorasCurso / this.getMetaHorasPorDia());
        System.out.println(this.getNome() + " completará o bootcamp em aproximadamente " + dias +
                " dia(s) com base em uma estimativa de " + this.getMetaHorasPorDia() + " horas de estudo por dia.");;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }
}
