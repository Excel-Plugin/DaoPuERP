package trapx00.lightx00.client.bl.clientbl;

import trapx00.lightx00.client.vo.salestaff.ClientVo;

public interface ClientQueryService {

    /**
     * Query clients which includes the words
     * @param query condition
     * @return the clients which corresponds to the query
     */
    public ClientVo[] query(String query);
}