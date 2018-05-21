/**
 * DTO para criação de um portador de crédito.
 */
export class CreateUpdatePortadorDTO {
    nome: string;
    email: string;
    cpf: string;
    rua: string;
    bairro: string;
    numero: string;
    complemento: string;
    cep: string;
    municipio: string;
    uf: string;
    dividaSerasa: string;
    dividaSpc: string;
    dividaCartorio: string;
}

/**
 * DTO com campos para busca de portadores de crédito.
 */
export class FindPortadorDTO {
    nome: string;
    cpf: string;
    aprovado: boolean;
    analisado: boolean;
}

/**
 * DTO com os dados obtidos de um portador de crédito.
 */
export class ResponsePortadorCreditoDTO {
    id: number;
    nome: string;
    email: string;
    cpf: string;
    rua: string;
    bairro: string;
    numero: string;
    complemento: string;
    cep: string;
    municipio: string;
    uf: string;
    dividaSerasa: number;
    dividaSpc: number;
    dividaCartorio: number;
    aprovado: boolean;
}