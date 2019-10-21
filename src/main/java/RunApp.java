import com.brillio.counter.NormalServiceCounter;
import com.brillio.counter.PremiumServiceCounter;
import com.brillio.counter.ServiceCounter;
import com.brillio.counter.TokenGenerationCounter;
import com.brillio.kafka.ConsumerKafka;

public class RunApp {

    public static void main(String[] args) {
        ConsumerKafka normalConsumerKafka = new ConsumerKafka("normalToken");
        ConsumerKafka premiumConsumerKafka = new ConsumerKafka("premiumToken");
        TokenGenerationCounter tokenGenerationCounter = new TokenGenerationCounter();

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
