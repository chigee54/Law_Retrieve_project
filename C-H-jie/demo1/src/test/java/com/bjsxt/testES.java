package com.bjsxt;


import com.bjsxt.pojo.People;
import com.bjsxt.pojo.Sentence;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class testES {

    @Autowired
    private ElasticsearchRestTemplate template;


//创建索引
    @Test
    public void test1(){
        IndexOperations indexOperations = template.indexOps(People.class);
        indexOperations.create();

//        定制mapping规则
        Document mapping = indexOperations.createMapping(People.class);
        indexOperations.putMapping(mapping);
    }


//删除索引
    @Test
    public void test2(){
        IndexOperations indexOperations = template.indexOps(People.class);
        boolean delete = indexOperations.delete();
        System.out.println("delete = " + delete);
    }

//插入文档
    @Test
    public void test3(){
        People people = new People("M5SUg3wBh_ojsCNarSMV", "李四", "我叫李四，今年六岁。因为我是蒙古人，所以我家里有四口人。" +
                "我写作业很慢，因为我写作业的时候，写了一点点就不写了，我一定要把这个习惯改掉。但是我学习成绩很好，在我们班当了学习小标兵。期末考试得了数学99，语文100。");
        People save = template.save(people);
        System.out.println(save);

    }


//批量插入文档
    @Test
    public void test4(){
        List<Sentence> list = new ArrayList<>();
        list.add(new Sentence("10","我真的很喜欢你 想和你有个很长的未来 一直肆无忌惮的爱你 想每天给你拥抱 在你不开心的时候陪在你身边哄你 和你每天腻歪在一起 每天跟你说早安晚安 都说时间会印证一切 我愿意让时间来证明我对你的爱 也希望你不要放弃我 不要敷衍我 牵着我的手再也不要分开 在二十几岁的年纪遇见了你"));
        list.add(new Sentence("20","笑死了，你是在打字嘛，为什么这么慢，你知道我等的有多急嘛，你在乎我吗，不，你不在乎，你只在乎你自己，好了，我知道了，我什么都懂了，原来我在你心里就是这个地位，不不不，我根本没进入到你的心里，这一切都是我自作多情，对不起，对不起，我不该打扰你，但是我还想你能理解我，所以你能理解吗"));
        list.add(new Sentence("30","早 我已经起床了，这个点起床的是游乐人间的成功者，是都市小说里的特种兵王，是科举考试的状元，是运动会的第一名，是英雄联盟的冠军中单，是篮球界的乔丹，是日漫里的主角，是生物学界的达尔文，侦探界的福尔摩斯，是练武界的天阶高手，修仙人中的准仙人，是陶醉在生活现实的享乐者，是天上掉馅饼也不抬头的世家贵族，是世间所有丑与恶的唾弃者，是世间所有美与好的创造者，每念此吾则心旷神怡。这个点还没起床的就是一帮懒狗,是废物，是成语里的酒囊饭袋，俗语里的臭鱼烂虾，是沉睡在虚无空间的逃避者，是半醉在生活现实的奉承者，是天上掉馅饼也不抬头的低头族，是夜夜笙歌的纨绔子弟，是玩物丧志的狺狺之犬。"));
        list.add(new Sentence("40","你说得对，可是老干妈的酸辣并不能为广东肠粉提鲜，反而黄焖鸡米饭更让我感觉吃得放心；而实际上俄罗斯的优势在于地广人稀，假设元神的成功可以带领中国单机游戏旭日东升，那么我觉得穿西装的话，还是打领带比较得体一些。"));
        list.add(new Sentence("50","好活，但是有点烂，不过也是挺好的，可惜对我来说比较烂，只是太好了，没体现出烂的感觉，所以相对好来说，有点烂，总体来说还是好，好中不足就是烂了点"));
        list.add(new Sentence("60","为什么撤回？撤回了什么？有什么是我不能知道的？还是你觉得我不能知道？我们俩的关系在你眼里就这么不值一提吗？那你觉得我们是什么关系？不会连这个你都不愿意跟我说吧？我真的破防了！得不到让我满意的答案我真的会发疯！"));

        Iterable<Sentence> save = template.save(list);
        System.out.println(save);
    }

// 删除操作
    @Test
    public void test5(){
        String delete = template.delete("30", Sentence.class);
        System.out.println(delete);
    }


//    全量修改
    @Test
    public void test6(){
        Sentence sentence = new Sentence("60", "我觉得你就是对我有感觉 从你时不时发照片我就发现了 你是想展现自己的魅力迷惑我让我喜欢上你 今天你发这张照片 其实内心只想得到我的回应吧 为了不让你的喜欢落空 我决定今天允许你和我交往");
        template.save(sentence);
    }

//   部分修改
    @Test
    public void test7(){
        String script = "ctx._source.name='张三'";
        UpdateQuery build = UpdateQuery.builder("1").withScript(script).build();
        IndexCoordinates indexCoordinates = IndexCoordinates.of("people");
        UpdateResponse update = template.update(build, indexCoordinates);
        System.out.println("update = " + update);

    }

//    主键查询
    @Test
    public  void test8(){
        Sentence sentence = template.get("30", Sentence.class);
        System.out.println(sentence);
    }


//    模糊查询
    @Test
    public void search1(){

        String keyword = "好";
        Query query = new NativeSearchQuery(QueryBuilders.queryStringQuery(keyword));
        SearchHits<Sentence> searchHits = template.search(query, Sentence.class);
        List<SearchHit<Sentence>> hits = searchHits.getSearchHits();

        List<Sentence> list = new ArrayList<>();
        for (SearchHit<Sentence> s : searchHits){
            list.add(s.getContent());
        }

        String r = "";
        for(int i = 0 ; i < list.size() ; i++)
        {
            r = r+list.get(i)+",\n";
        }

        System.out.println(r);
    }

}
