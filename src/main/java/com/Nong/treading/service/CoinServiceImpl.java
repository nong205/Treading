package com.Nong.treading.service;

import com.Nong.treading.modal.Coin;
import com.Nong.treading.repository.CoinRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
        // Create a RestTemplate instance to make the API call
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            List<Coin> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {
            });
            return coinList;

        } catch (HttpClientErrorException | HttpServerErrorException | JsonProcessingException e) {
            throw new RuntimeException("Error fetching coin list: " + e.getMessage(), e);
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "market_chart?vs_currency=usd&days=" + days;
        // Create a RestTemplate instance to make the API call
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;
        // Create a RestTemplate instance to make the API call
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.get("market_data");

            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(jsonNode.get("market_cap_rank").asInt());
            coin.setFullyDilutedValuation(marketData.get("fully_diluted_valuation").get("usd").asLong());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").get("usd").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change2_24h").get("usd").asDouble());

            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
            coin.setTotalSupply(marketData.get("total_supply").get("usd").asLong());
            coinRepository.save(coin);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin> OptionalCoin = coinRepository.findById(coinId);
        if (OptionalCoin.isEmpty()) throw new Exception("Coin not found with id: " + coinId);
        return OptionalCoin.get();
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
//        String url = "https://api.coingecko.com/api/v3/coins/"+coinId+"market_chart?vs_currency=usd&days=" + days;
//        // Create a RestTemplate instance to make the API call
//        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            HttpHeaders headers = new HttpHeaders();
//
//            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//            return response.getBody();
//
//        }catch (HttpClientErrorException | HttpServerErrorException e) {
//            throw new Exception(e.getMessage());
//        }
        return null;
    }

    @Override
    public String getTop50CoinsByMarketCapRank() throws Exception {
        return "";
    }

    @Override
    public String getTradingCoins() throws Exception {
        return "";
    }
}
