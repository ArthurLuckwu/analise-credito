import { ResponsePortadorCreditoDTO } from "../portador/portador";

/**
 * DTO para criação de uma análise de crédito.
 */
export class CreateUpdateCreditoDTO {
    creditoConcedido: string;
    aprovado: boolean;
}

/**
 * DTO com os dados obtidos de uma análise de crédito.
 */
export class ResponseCreditoDTO {
    creditoConcedido: string;
    portadorCredito: ResponsePortadorCreditoDTO
}
