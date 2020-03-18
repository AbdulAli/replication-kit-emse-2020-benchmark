df = read.csv("TimeBuckets1.csv")

library(ggplot2)

#Abandoned colorful graph
#ggplot(df, aes(fill=Project, y=Frequency, x=Time))+ 
#  geom_bar( stat="identity")+ theme(axis.text.x = element_text(angle=45)) + 
#  xlab("Time Buckets") + ylab("Project Versions") + 
#  labs(color = "Projects",shape ="Projects", fill = "Projects")

plot1 <- ggplot(df, aes(x=Project, y=Time, size=Frequency)) +     
  geom_point(alpha=0.7) +      
  scale_colour_continuous(guide = FALSE) + theme(axis.text.x = element_text(angle = 25)) + 
  labs(size="Number of versions") + theme(legend.position="top") + 
  labs(x="Projects") + labs(y="Time Buckets")

pdf("TimeBucketDis.pdf",width = 5,height = 4)

plot1

dev.off()