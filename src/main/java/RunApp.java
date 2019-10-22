import com.brillio.PropertiesLoader;
import com.brillio.counter.NormalServiceCounter;
import com.brillio.counter.PremiumServiceCounter;
import com.brillio.counter.ServiceCounter;
import com.brillio.counter.TokenGenerationCounter;
import com.brillio.queue.ConsumerKafka;
import com.brillio.queue.ProducerKafka;

public class RunApp {

  public static void main(String[] args) {
    ProducerKafka producerKafka = new ProducerKafka();
    ConsumerKafka normalConsumerKafka = new ConsumerKafka("normal");
    ConsumerKafka premiumConsumerKafka = new ConsumerKafka("premium");
    TokenGenerationCounter tokenGenerationCounter = new TokenGenerationCounter(
        PropertiesLoader.loadProperties(), producerKafka);

    System.out.println(tokenGenerationCounter.generatePremiumTokenAfterVerification());

    System.out.println(tokenGenerationCounter.generateNormalTokenAfterVerification());
    System.out.println(tokenGenerationCounter.generateNormalTokenAfterVerification());
    System.out.println(tokenGenerationCounter.generateNormalTokenAfterVerification());

    ServiceCounter normalServiceCounter = new NormalServiceCounter(normalConsumerKafka);
    ServiceCounter premiumServiceCounter = new PremiumServiceCounter(premiumConsumerKafka);

    System.out.println(normalServiceCounter.nextToken());
    System.out.println(normalServiceCounter.nextToken());
    System.out.println(premiumServiceCounter.nextToken());
    System.out.println(normalServiceCounter.nextToken());
    System.out.println(premiumServiceCounter.nextToken());


  }
}
