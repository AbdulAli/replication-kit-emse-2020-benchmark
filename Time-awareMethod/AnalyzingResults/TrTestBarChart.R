library(dplyr)
library(ggplot2)

data <- read.csv("stats.csv")

df<-summarise_at(group_by(data,configuration,window,split,type),vars(size,defects),funs(mean(.,na.rm=TRUE)))

# test <- df$size_test
# testName <- c(rep('Test',length(test)))
# tr <- df$size_training
# trName <- c(rep('Tr',length(tr)))
# 
# testTrListName <- c(testName,trName)
# testTrList <- c(test,tr)
# 
# df = data.frame(configuration = df$configuration, k = df$windowSize, split = df$split, type = testTrListName, size = testTrList)

cc <- df[df$configuration == 1,]
cc<-summarise_at(group_by(cc,configuration,window,type),vars(size),funs(max(.,na.rm=TRUE)))
cc$window = factor(cc$window, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
ccPlotWindow <- ggplot(cc, aes(x = window, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Window Size") + ylab("Instances") + labs(fill = "Set")

cc <- df[df$configuration == 1,]
cc<-summarise_at(group_by(cc,configuration,split,type),vars(size),funs(max(.,na.rm=TRUE)))
cc$split = factor(cc$split, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
ccPlotTime <- ggplot(cc, aes(x = split, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Split point in split") + ylab("Instances") + labs(fill = "Set")


ic <- df[df$configuration == 2,]
ic<-summarise_at(group_by(ic,configuration,window,type),vars(size),funs(max(.,na.rm=TRUE)))
ic$window = factor(ic$window, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
icPlotWindow <- ggplot(ic, aes(x = window, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Window Size") + ylab("Instances") + labs(fill = "Set")

ic <- df[df$configuration == 2,]
ic<-summarise_at(group_by(ic,configuration,split,type),vars(size),funs(max(.,na.rm=TRUE)))
ic$split = factor(ic$split, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
icPlotTime <- ggplot(ic, aes(x = split, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Split point in split") + ylab("Instances") + labs(fill = "Set")

ci <- df[df$configuration == 3,]
ci<-summarise_at(group_by(ci,configuration,window,type),vars(size),funs(max(.,na.rm=TRUE)))
ci$window = factor(ci$window, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
ciPlotWindow <- ggplot(ci, aes(x = window, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Window Size") + ylab("Instances") + labs(fill = "Set")

ci <- df[df$configuration == 3,]
ci<-summarise_at(group_by(ci,configuration,split,type),vars(size),funs(max(.,na.rm=TRUE)))
ci$split = factor(ci$split, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
ciPlotTime <- ggplot(ci, aes(x = split, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Split point in split") + ylab("Instances") + labs(fill = "Set")

ii <- df[df$configuration == 4,]
ii<-summarise_at(group_by(ii,configuration,split,type),vars(size),funs(max(.,na.rm=TRUE)))
ii$split = factor(ii$split, levels = c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19))
iiPlotTime <- ggplot(ii, aes(x = split, y = size, fill = type, label = size)) +
  geom_bar(stat = "identity") + xlab("Split point in split") + ylab("Instances") + labs(fill = "Set")


#pdfName <- "TestTrainSize.pdf"
#pdf(pdfName,8,11)

ggarrange(ccPlotWindow + theme(axis.text.x = element_text(angle=90,size=5)),
          ccPlotTime+ theme(axis.text.x = element_text(angle=90,size=5)),
          icPlotWindow + theme(axis.text.x = element_text(angle=90,size=5)),
          icPlotTime+ theme(axis.text.x = element_text(angle=90,size=5)),
          ciPlotWindow + theme(axis.text.x = element_text(angle=90,size=5)),
          ciPlotTime + theme(axis.text.x = element_text(angle=90,size=5)),
          iiPlotTime + theme(axis.text.x = element_text(angle=90,size=5)),
          labels = c("CC", "CC", "IC","IC", "CI", "CI", "II"), 
          font.label = list(size = 8),
          ncol = 2, nrow = 4, common.legend = TRUE, legend="bottom")
#dev.off()
