package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum Job {

    UNKNOWN, // -1 - Não sabe/não responde

    MILITARY_FORCES, // 01 - Forças Armadas

    SUPERIOR_BOARDING_OF_PUBLIC_ADMINISTRATION, // 11 - Quadros superiores da administração publica

    COMPANIES_DIRECTORS, // 12 - Diretores de Empresas

    SMALL_COMPANIES_DIRECTORS_AND_MANAGERS, // 13 - Diretores e Gerentes de pequenas Empresas

    PHYSICS_MATHS_AND_ENGINEERING_SPECIALISTS, // 21 - Especialistas das Ciências Físicas, Matemáticas e Engenharia

    LIFE_AND_HEALTH_SPECIALISTS, // 22 - Especialistas das Ciências da Vida e Profissionais de Saúde

    TEACHERS, // 23 - Docentes do Ensino Secundário, Superior e Similares

    INTELECTUAL_SPECIALISTS, // 24 - Outros Especialistas das Profissões Intelectuais e Cientificas

    PHYSIC_CHEMISTRY_AND_ENGINEERING_PROFESSIONALS, // 31 - Técnicos, Profissionais nível intermédio da Física, Química e Engenharia

    INTERMEDIATE_LIFE_AND_HEALTH_PROFESSIONALS, // 32 - Profissionais de nível intermédio das ciências da Vida e Saúde

    INTERMEDIATE_TEACHERS, // 33 - Profissionais de nível intermédio do Ensino

    OTHER_INTERMEDIATE_PROFESSIONALS, // 34 - Outros técnicos e profissionais nível intermédio

    OFFICER_EMPLOYEES, // 41 - Empregados de escritório

    SERVICE_DESK_EMPLOYEES, // 42 - Empregados de recepção, caixas, bilheteiros e similares

    SECURITY_PROFESSIONALS, // 51 - Pessoal dos Serviços directos e particulares de protecção e segurança

    SALES_PROFESSIONALS, // 52 - Manequins, vendedores e Demonstradores

    QUALIFIED_FARMERS, // 61 - Agricultores, Trabalhadores qualificados da agricultura, criação e pescas

    BASIC_FARMERS, // 62 - Agricultores, Pescadores - agricultura e pesca de subsistência

    INDUSTRY_WORKERS, // 71 - Operários, artífices e similares da industria extractiva e construção civil

    METALWORKING_WORKERS, // 72 - Trabalhadores da Metalurgia, Metalomecânica e similares

    CRAFTSMEN_WORKERS, // 73 - Mecânicos de precisão, Oleiros, Vidreiros, Artesãos, e artes gráficas

    OTHER_CRAFTSMEN_WORKERS, // 74 - Outros operários, artífices e trabalhadores similares

    FIXED_FACILITIES_WORKERS, // 81 - Operadores de instalações fixas e similares

    MACHINERY_WORKERS, // 82 - Operadores de máquinas e trabalhadores da montagem

    DRIVERS, // 83 - Condutores de veículos, embarcações e equipamentos pesados móveis

    UNQUALIFIED_SERVICES_AND_COMMERCE_WORKERS, // 91 - Trabalhadores não qualificados dos serviços e comércio

    UNQUALIFIED_FARMING_AND_FISHING_WORKERS, // 92 - Trabalhadores não qualificados da agricultura e pescas

    UNQUALIFIED_MINING_WORKERS, // 93 - Trabalhadores não qual. das minas, construção civil e obras públicas

    WRONGLY_DEFINED_PROFESSIONS; // 99 - Profissões mal definidas

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}