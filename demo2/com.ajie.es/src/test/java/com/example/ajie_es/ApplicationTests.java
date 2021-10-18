package com.example.ajie_es;

import com.alibaba.fastjson.JSON;
import com.example.ajie_es.pojo.People;
import com.example.ajie_es.pojo.Sentence;
import lombok.SneakyThrows;
import org.apache.lucene.index.IndexReader;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class ApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    //索引创建
    @SneakyThrows
    @Test
    void test1() {
        CreateIndexRequest request = new CreateIndexRequest("sentence");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("createIndexResponse = " + createIndexResponse);
    }
    //查询索引是否存在
    @SneakyThrows
    @Test
    void test2(){
        GetIndexRequest getIndexRequest = new GetIndexRequest("sentence");
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println("exists = " + exists);
    }
//    删除索引
    @SneakyThrows
    @Test
    void  test3(){
        DeleteIndexRequest request = new DeleteIndexRequest("sentence");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("delete = " + delete.isAcknowledged());
    }

//   添加文档
    @Test
    void test4() throws IOException {
        People people = new People("1", "今天也是和平的一天，我带着我的好儿子一起走在沙滩上，风很温和，阳光和煦，海鸥随着海风上下翻飞。我感受到了一股内心的平静");
        IndexRequest request = new IndexRequest("sentence");
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        IndexRequest source = request.source(JSON.toJSONString(people), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println("indexResponse = " + indexResponse);
    }

//    批量插入文档
    @Test
    void test5() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<People> list = new ArrayList<>();
        list.add(new People("10","我真的很喜欢你 想和你有个很长的未来 一直肆无忌惮的爱你 想每天给你拥抱 在你不开心的时候陪在你身边哄你 和你每天腻歪在一起 每天跟你说早安晚安 都说时间会印证一切 我愿意让时间来证明我对你的爱 也希望你不要放弃我 不要敷衍我 牵着我的手再也不要分开 在二十几岁的年纪遇见了你"));
        list.add(new People("20","笑死了，你是在打字嘛，为什么这么慢，你知道我等的有多急嘛，你在乎我吗，不，你不在乎，你只在乎你自己，好了，我知道了，我什么都懂了，原来我在你心里就是这个地位，不不不，我根本没进入到你的心里，这一切都是我自作多情，对不起，对不起，我不该打扰你，但是我还想你能理解我，所以你能理解吗"));
        list.add(new People("30","早 我已经起床了，这个点起床的是游乐人间的成功者，是都市小说里的特种兵王，是科举考试的状元，是运动会的第一名，是英雄联盟的冠军中单，是篮球界的乔丹，是日漫里的主角，是生物学界的达尔文，侦探界的福尔摩斯，是练武界的天阶高手，修仙人中的准仙人，是陶醉在生活现实的享乐者，是天上掉馅饼也不抬头的世家贵族，是世间所有丑与恶的唾弃者，是世间所有美与好的创造者，每念此吾则心旷神怡。这个点还没起床的就是一帮懒狗,是废物，是成语里的酒囊饭袋，俗语里的臭鱼烂虾，是沉睡在虚无空间的逃避者，是半醉在生活现实的奉承者，是天上掉馅饼也不抬头的低头族，是夜夜笙歌的纨绔子弟，是玩物丧志的狺狺之犬。"));
        list.add(new People("40","你说得对，可是老干妈的酸辣并不能为广东肠粉提鲜，反而黄焖鸡米饭更让我感觉吃得放心；而实际上俄罗斯的优势在于地广人稀，假设元神的成功可以带领中国单机游戏旭日东升，那么我觉得穿西装的话，还是打领带比较得体一些。"));
        list.add(new People("50","好活，但是有点烂，不过也是挺好的，可惜对我来说比较烂，只是太好了，没体现出烂的感觉，所以相对好来说，有点烂，总体来说还是好，好中不足就是烂了点"));
        list.add(new People("60","为什么撤回？撤回了什么？有什么是我不能知道的？还是你觉得我不能知道？我们俩的关系在你眼里就这么不值一提吗？那你觉得我们是什么关系？不会连这个你都不愿意跟我说吧？我真的破防了！得不到让我满意的答案我真的会发疯！"));
        list.add(new People("9","国庆节快乐，大家都快乐"));
        list.add(new People("81","《西游记》，又称《西游释厄传》，是古代明朝第一部浪漫主义章回体长篇神魔小说，全书58万5千字（世德堂本），《中国四大名著》之一、《四大奇书》之一。成书于16世纪明朝中叶，一般认为作者是明朝的吴承恩。书中讲述唐三藏与徒弟孙悟空、猪八戒和沙悟净等师徒四人前往西天取经的故事，表现了惩恶扬善的古老主题，也有观点认为西游记是暗讽权力官场的讽刺小说。"));
        list.add(new People("82","《西游记》自问世以来，在中国及世界各地广为流传，被翻译成多种语言。在中国，乃至亚洲部分地区《西游记》家喻户晓，其中孙悟空、唐僧、猪八戒、沙僧等人物和“大闹天宫”、“三打白骨精”、“孙悟空三借芭蕉扇”等故事尤其为人熟悉。几百年来，西游记被改编成各种地方戏曲、电影、电视剧、动画片、漫画等，版本繁多。"));
        list.add(new People("83","《西游记》全书共一百回，可分为四个长短不一的部分。开头诗为“混沌未分天地乱，茫茫渺渺无人见。自从盘古破鸿蒙，开辟从兹清浊辨。覆载群生仰至仁，发明万物皆成善。欲知造化会元功，须看《西游释厄传》。”第一部分是第一回至第七回，介绍故事主角孙悟空的诞生，孙悟空是吸收天地精华而生的石猴，因为向菩提祖师学法而得道，能通地煞七十二变、乘斤斗云、使如意金箍棒，他骄傲地自称为齐天大圣，桀骜不驯的行为让天庭十分头痛。在他大闹天宫之后，遭到如来佛祖降伏，如来佛祖将他压在五行山下长达五百年。"));
        list.add(new People("84","《水浒传》，是以白话文写成的章回小说，列为中国古典四大文学名著之一，六才子书之一。其内容讲述北宋山东梁山泊以宋江为首的梁山好汉，由被逼落草，发展壮大，直至受到朝廷招安，东征西讨的历程。又称《忠义水浒全传》、《江湖豪客传》、《水浒全传》，一般简称《水浒》，全书定型于明朝。作者历来有争议[1]，一般认为是施耐庵所著，而罗贯中则做了整理，金圣叹删减为七十回本。"));
        list.add(new People("85","北宋（960年2月4日—1127年3月20日），古中国朝代。自赵匡胤发动陈桥兵变后周周恭帝禅让（960年）始，靖康二年（1127年）金兵攻入开封，俘虏宋徽宗、宋钦宗，标志着北宋结束。北宋建都汴梁（今河南开封市）。宋高宗在应天府称帝，南宋开始，南宋与北宋合称“两宋”或“宋朝”共319年。[1]。依据五行相生的顺序，后周的“木”德之后为“火”德，因此宋朝以“火”为五行德运，并取红色为王朝正色。[2]"));
        list.add(new People("86","北宋的基本国策是“重文轻武”，这个政策对宋朝有利有弊，好处乃在于使北宋初期政治、经济等各方面都比较安定，尤其是没有宦官专权、地方割据等祸事。即使帝王中多表现平平，但也无损国家的繁荣安定。而坏处则是令北宋在军事上接连挫败，连同南宋共三百多年，整个宋朝的历史重心，都是战事的挫败和退却。"));

        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest("sentence")
                    .id(""+i+1)
                    .source(JSON.toJSONString(list.get(i)),XContentType.JSON));
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("bulk.hasFailures() = " + bulk.hasFailures());
    }

}
