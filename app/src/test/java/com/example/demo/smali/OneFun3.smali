.class public Lcom/example/mylibrary3/OneFun3;
.super Ljava/lang/Object;
.source "OneFun3.java"

# interfaces
.implements Lcom/example/mylibrary3/Student;


# static fields
.field public static gal_num:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 4
    const/4 v0, 0x5

    sput v0, Lcom/example/mylibrary3/OneFun3;->gal_num:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public add3(II)Ljava/lang/String;
    .locals 3
    .param p1, "a"    # I
    .param p2, "b"    # I

    .prologue
    .line 7
    sget v0, Lcom/example/mylibrary3/OneFun3;->gal_num:I

    add-int/lit8 v0, v0, 0x1

    sput v0, Lcom/example/mylibrary3/OneFun3;->gal_num:I

    .line 9
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "3\u8fd4\u56de\u7ed3\u679c:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    add-int v1, p1, p2

    sget v2, Lcom/example/mylibrary3/OneFun3;->gal_num:I

    add-int/2addr v1, v2

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public walk(ILjava/lang/String;)V
    .locals 3
    .param p1, "a"    # I
    .param p2, "b"    # Ljava/lang/String;

    .prologue
    .line 15
    const/16 v0, 0x43

    .line 16
    .local v0, "loc_num":I
    const/4 v1, 0x5

    if-le p1, v1, :cond_0

    .line 17
    add-int/lit8 v0, v0, 0x8

    .line 20
    :goto_0
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 21
    return-void

    .line 19
    :cond_0
    add-int/lit8 v0, v0, 0x4

    goto :goto_0
.end method
